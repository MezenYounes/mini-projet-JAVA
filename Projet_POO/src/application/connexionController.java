package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class connexionController implements Initializable {
	
    @FXML
    private Button btncon;
    
    

    @FXML
    private TextField tname;

    @FXML
    private PasswordField tpass;
    
    @FXML
    void connecter(ActionEvent event) {
    	System.out.println("Connexion attempt"); // Debugging message
        Connection cn = DBconnexion.getcn();
        PreparedStatement stmt;
        try {
            stmt = cn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            stmt.setString(1, tname.getText());
            stmt.setString(2, tpass.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful"); // Debugging message
                try {
                    // Charger le fichier FXML pour la fenêtre des médicaments
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("medicament.fxml"));
                    Parent root = loader.load();

                    // Créer une nouvelle scène et l'afficher dans une nouvelle fenêtre
                    Stage stage = new Stage();
                    stage.setTitle("Gestion des Médicaments");
                    stage.setScene(new Scene(root));
                    stage.show();

                    // Fermer la fenêtre de connexion
                    Stage loginStage = (Stage) tname.getScene().getWindow();
                    loginStage.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Gérer l'échec de connexion (message d'erreur, etc.)
            	 Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Erreur de connexion");
                 alert.setHeaderText(null);
                 alert.setContentText("Identifiants incorrects");
                 alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
		
		
		
	}
	




