package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class medicamentController implements Initializable {
	

    @FXML
    private Button btnclear;
    @FXML
    private Button btncalcul;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnexport;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField prix;

    @FXML
    private TextField quan;

    @FXML
    private TableView<medicament> table;
    
    @FXML
    private TableColumn<medicament, Integer> colid;

    @FXML
    private TableColumn<medicament, String> colnom;

    @FXML
    private TableColumn<medicament, Double> colprix;

    @FXML
    private TableColumn<medicament, Double> colquan;
    @FXML
    private Label total;
    
    
    @FXML
    void calculer(ActionEvent event) {
        double totalAmount = 0.0;
        for (medicament md : table.getItems()) {
            totalAmount += md.getQuantite() * md.getPrix();
        }
        total.setText(String.format("%.2f", totalAmount));
    }


    @FXML
    void print(ActionEvent event) {
    	btnclear.setOnAction(actionEvent ->{
    		PrinterJob job= PrinterJob.createPrinterJob();
    		if(job !=null && job.showPrintDialog(btnclear.getScene().getWindow())) {
    			boolean success=job.printPage(table);
    			if (success) {
    				job.endJob();
    			}
    		}
    	});
    	

    }
    public void clearr() {
    	id.setText(null);
    	name.setText(null);
    	quan.setText(null);
    	prix.setText(null);
    	btnsave.setDisable(false);
    	
    }

    @FXML
    void creermed(ActionEvent event) {
    	Connection cn = DBconnexion.getcn();
    	PreparedStatement stmt;
    	String query="insert into medicament values (?,?,?,?)";
    	 try {
    			stmt = cn.prepareStatement(query);
    			stmt.setInt(1, Integer.parseInt(id.getText()));
    			stmt.setString(2,name.getText());
    			stmt.setDouble(3, Double.parseDouble(quan.getText()));
    			stmt.setDouble(4, Double.parseDouble(prix.getText()));
    			
    			stmt.executeUpdate();
    			clearr();
    			showmedicament();
    			
    	 } catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    			
    	
    	

    }

    @FXML
    void export(ActionEvent event) {
        List<medicament> medicaments = table.getItems(); // Obtenir les éléments du TableView
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\fichiers\\medicament.txt"))) {
            // Écrire l'en-tête
            writer.write("ID,Nom,Quantité,Prix");
            writer.newLine();
            
            // Écrire les données
            for (medicament md : medicaments) {
                writer.write(md.getId() + "," + md.getNom() + "," + md.getQuantite() + "," + md.getPrix());
                writer.newLine();
            }
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exportation Réussie");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été exportées avec succès !");
            alert.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void getData(MouseEvent event) {
    	medicament md=table.getSelectionModel().getSelectedItem();
    	id.setText(String.valueOf(md.getId()));
    	name.setText(String.valueOf(md.getNom()));
    	quan.setText(String.valueOf(md.getQuantite()));
    	prix.setText(String.valueOf(md.getPrix()));
    	btnsave.setDisable(true);
    	
    	

    }

    @FXML
    void modifier(ActionEvent event) {
    	Connection cn = DBconnexion.getcn();
    	PreparedStatement stmt;
    	String update="update medicament set nom=?,quantite=?,prix=? where id=?";
    	try {
			stmt = cn.prepareStatement(update);
			
			stmt.setString(1,name.getText());
			stmt.setDouble(2, Double.parseDouble(quan.getText()));
			stmt.setDouble(3, Double.parseDouble(prix.getText()));
			stmt.setInt(4, Integer.parseInt(id.getText()));
			stmt.executeUpdate();
			showmedicament();
			clearr();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }

    @FXML
    void supp(ActionEvent event) {
    	Connection cn = DBconnexion.getcn();
    	PreparedStatement stmt;
    	String delete="Delete from medicament where id =?";
    	try {
			stmt = cn.prepareStatement(delete);
			stmt.setInt(1, Integer.parseInt(id.getText()));
			stmt.executeUpdate();
			showmedicament();
			clearr();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }
    public ObservableList<medicament> getmedicament(){
    	Connection cn = DBconnexion.getcn();
    	PreparedStatement stmt;
    ObservableList<medicament> medicament = FXCollections.observableArrayList();
    String query="select * from medicament";
    try {
		stmt = cn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			medicament md=new medicament();
			
			md.setId(rs.getInt("id"));
			md.setNom(rs.getString("nom"));
			md.setQuantite(rs.getDouble("quantite"));
			md.setPrix(rs.getDouble("prix"));
			
			medicament.add(md);
			
		
		}
			
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    return medicament;
    
    
    }
    public void showmedicament() {
    	 ObservableList<medicament> list=getmedicament();
    	 table.setItems(list);
    	 colid.setCellValueFactory(new PropertyValueFactory<medicament,Integer>("id"));
    	 colnom.setCellValueFactory(new PropertyValueFactory<medicament, String>("nom"));
    	 colprix.setCellValueFactory(new PropertyValueFactory<medicament, Double>("prix"));
    	 colquan.setCellValueFactory(new PropertyValueFactory<medicament,Double>("quantite"));
    	 
    	
    	
    	
    	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showmedicament();
		
	}

}
