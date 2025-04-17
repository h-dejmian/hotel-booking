import com.example.hotelbooking.controllers.AddHotelController;
import com.example.hotelbooking.controllers.MainController;
import com.example.hotelbooking.dao.HotelDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class MainControllerFxTest {

    @Test
    void testAddHotel_opensWindowAndSetsDependencies(FxRobot robot) throws Exception {
        // Given
        AddHotelController mockedAddHotelController = mock(AddHotelController.class);
        FXMLLoader mockedLoader = mock(FXMLLoader.class);

        when(mockedLoader.getController()).thenReturn(mockedAddHotelController);
        when(mockedLoader.load()).thenReturn(new AnchorPane());

        MainController controllerSpy = spy(new MainController());
        doReturn(mockedLoader)
                .when(controllerSpy)
                .createFXMLLoader("/com/example/hotelbooking/add-hotel-view.fxml");

        // When
        robot.interact(controllerSpy::addHotel);

        // Then
        verify(mockedAddHotelController).setMainController(controllerSpy);
        verify(mockedAddHotelController).setHotelDao(any());
    }
}