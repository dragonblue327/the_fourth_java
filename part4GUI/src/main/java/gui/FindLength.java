package gui;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXML;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.geometry.HPos;
import javafx.geometry.VPos;

import Geometry.IMoveable;
import Geometry.IShape; 
import Geometry.Point2D;
import Geometry.Segment;
import Geometry.Circle;
import Geometry.Polyline;
import Geometry.NGon;
import Geometry.TGon;
import Geometry.QCon;
import Geometry.Rectangle;
import Geometry.Trapeze;
import lombok.extern.java.Log;

@Log
public class FindLength
{

		class ObservableOption
		{
				public ObservableOption(int i, String s)
				{
						this.i = i;
						this.s = s;
				}
				public int i;
				public String s;
				public String toString()
				{
						return s;
				}
		}

		private int countFigures;

		@FXML
		private Button bCancel;

		@FXML
		private ComboBox cbFigures;

		@FXML
		private void initialize() throws Exception
		{
				countFigures = CController.figures.size();
				ObservableList<ObservableOption> figureNames = FXCollections.observableArrayList();
				for(int i = 0; i < countFigures; ++i)
				{
						figureNames.add(new ObservableOption(i,CController.figures.get(i).toString()));
				}

				cbFigures.setItems(figureNames);
		}
		

		@FXML
		private void confirm()
		{
				try
				{
						ObservableOption fig = (ObservableOption)cbFigures.getValue();	
						if (fig == null)
								throw (new Exception("Figured hasn't been choosen"));

						CController.numValue1 = CController.figures.get(fig.i).length();
						CController.textValue1 = "Length:";
						CController.markedFigureIndexes = new int[] {fig.i};

						CController.showAlert("Length was found","info",AlertType.INFORMATION);
						log.info("Length was found. Figure:\n"+CController.figures.get(fig.i).toString());

						Stage stage = (Stage)bCancel.getScene().getWindow(); 
						stage.close();
				}

				catch (Exception e)
				{
						CController.showAlert(e.getMessage(),"err",AlertType.ERROR);
				}
		}

		@FXML
		private void cancel()
		{
				CController.showAlert("Finished mesuarements","info",AlertType.INFORMATION);
				Stage stage = (Stage)bCancel.getScene().getWindow(); 
				stage.close();
		}
}
