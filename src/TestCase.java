import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

//
public class TestCase {

	@Test
	public void testmodel() {
		Model model = new Model("easy");
		assertEquals(model.getCurrency(), 15);
		Model model1 = new Model("hard");
		assertEquals(model1.getCurrency(), 10);
		assertEquals(model1.getBlood(), 100);

		assertEquals(model.addTower(0, 0, 1), 1);
		assertEquals(model.addTower(0, 0, 1), 0);
		assertEquals(model.removeTower(0, 0), 1);
		assertEquals(model.addCurrency(1), 16);
		assertEquals(model.subtractCurrency(1), 15);

		model.setBlood(1);
		assertEquals(model.getBlood(), 1);
	}

	@Test
	public void testController() {
		Controller controller = new Controller(new Model("easy"));
		assertEquals(controller.getNewTowerPos(), null);
		assertEquals(controller.getCurrency(), 15);
		assertEquals(controller.getBlood(), 100);

		assertEquals(controller.buyTower(1), 14);
		controller.arrived(10);

		assertEquals(controller.getBlood(), 0);
		assertEquals(controller.buyTower(1), -1);
		int[] a = controller.getPos(71, 4);

		assertEquals(a[0], 62245902);
		assertEquals(a[1], 1);
		controller.mouseMoved(0, 0);
		assertEquals(controller.getNewTowerPos(), null);
		controller.mouseMoved(71, 72);
		assertEquals(controller.getNewTowerPos(), null);
		controller.mouseMoved(70, 71);

		assertEquals(controller.addTower(), 1);
		assertEquals(controller.removeTower(0, 0), 1);
		assertEquals(controller.cancelBuying(), 15);

	}
}
