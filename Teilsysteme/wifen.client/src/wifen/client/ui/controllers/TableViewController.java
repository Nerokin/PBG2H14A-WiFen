package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class TableViewController extends Pane
{
	// Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	private ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
	private int columnCount = 0;

	// Injected node
	@FXML
	private TableView<ObservableList<String>> tblView;

	// Constructor
	/**
	 * Put description here
	 * 
	 * @param data
	 * @throws IOException
	 */
	public TableViewController(String[][] data) throws IOException
	{
		super();

		// Calculate column count
		columnCount = data[0].length;
		
		// Create row-data
		for(String[] row : data)
		{
			ObservableList<String> rowList = FXCollections.observableArrayList();
			for(String cell : row)
			{
				rowList.add(cell);
			}
			this.data.add(rowList);
		}

		// Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("/fxml/TableView.fxml"));
		getFXMLLoader().setController(this);

		// Load the View
		getFXMLLoader().load();
	}

	// Initialization
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize()
	{
		// Add columns
		for(int i = 0; i < columnCount; i++)
		{
			// We are using non property style for making dynamic table
			final int j = i;
			TableColumn<ObservableList<String>, String> col = new TableColumn<ObservableList<String>, String>("" + i);
			col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>()
					{
						public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param)
						{
							return new SimpleStringProperty(param.getValue().get(j).toString());
						}

					});

			tblView.getColumns().addAll(col);
		}

		// Set data
		tblView.setItems(data);

		// Bind listeners
		this.widthProperty().addListener(this::OnWidthChanged);
		this.heightProperty().addListener(this::OnHeightChanged);
	}

	// Events
	/**
	 * Put description here
	 * 
	 * @param observableValue
	 * @param oldSceneWidth
	 * @param newSceneWidth
	 */
	private void OnWidthChanged(
			ObservableValue<? extends Number> observableValue,
			Number oldSceneWidth, Number newSceneWidth)
	{
		tblView.setPrefWidth((double) newSceneWidth - 2);
	}

	/**
	 * Put description here
	 * 
	 * @param observableValue
	 * @param oldSceneHeight
	 * @param newSceneHeight
	 */
	private void OnHeightChanged(
			ObservableValue<? extends Number> observableValue,
			Number oldSceneHeight, Number newSceneHeight)
	{
		tblView.setPrefHeight((double) newSceneHeight - 2);
	}

	// Getter & Setter
	public FXMLLoader getFXMLLoader()
	{
		return fxmlLoader.get();
	}

	public void setFXMLLoader(FXMLLoader value)
	{
		fxmlLoader.set(value);
	}

	public ReadOnlyObjectProperty<FXMLLoader> fxmlLoaderProperty()
	{
		return fxmlLoader;
	}
}
