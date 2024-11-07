package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void testDexterityVest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);

		//simulate 10 more days
		for (int i = 0; i < 10; i++) {
			inn.oneDay();
		}

		//access a list of items, get the quality of the one set
		items = inn.getItems();
		quality = items.get(0).getQuality();

		//assert quality has decreased by 9+2 = 11 since the sellIn value is 0
		assertEquals("Failed quality for Dexterity Vest", 8, quality);

		//simulate 5 more days
		for (int i = 0; i < 5; i++) {
			inn.oneDay();
		}

		//access a list of items, get the quality of the one set
		items = inn.getItems();
		quality = items.get(0).getQuality();

		//assert quality has reached 0 but not gone below
		assertEquals("Failed quality for Dexterity Vest", 0, quality);
	}
	
	@Test
	public void testSulfuras() {
		//Create an inn, add a sulfular item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		inn.oneDay();

		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();

		//assert quality has not changed and sellIn has is still 0
		assertEquals("Failed quality for Sulfuras", 80, quality);
		assertEquals("Failed sellIn for Sulfuras", 0, items.get(0).getSellIn());
	}

	@Test
	public void testAgedBrie() {
		//Create an inn, add an aged brie with sellIn value of 1,
		//and simulate two days
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 1, 0));
		inn.oneDay();
		inn.oneDay();

		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();

		//assert quality has increased by one and sellIn has decreased by one
		assertEquals("Failed quality for Aged Brie", 3, quality);

		//Asser that quality never goes above 50
		for (int i = 0; i < 50; i++) {
			inn.oneDay();
		}
		assertEquals("Failed quality for Aged Brie", 50, items.get(0).getQuality());
	}

	@Test
	public void testBackstagePasses() {
		//Create an inn, add a backstage pass with sellIn value of 15
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		//Simulate 5 days
		for (int i = 0; i < 5; i++) {
			inn.oneDay();
		}

		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();

		//assert quality has increased by 5 and sellIn has decreased by 5
		assertEquals("Failed quality for Backstage Passes", 25, quality);
		assertEquals("Failed sellIn for Backstage Passes", 10, items.get(0).getSellIn());

		//Simulate 5 more days
		for (int i = 0; i < 5; i++) {
			inn.oneDay();
		}

		//access a list of items, get the quality of the one set
		items = inn.getItems();
		quality = items.get(0).getQuality();

		//assert quality has increased by 10 and sellIn has decreased by 5
		assertEquals("Failed quality for Backstage Passes", 35, quality);
		assertEquals("Failed sellIn for Backstage Passes", 5, items.get(0).getSellIn());

		//Simulate 5 more days
		for (int i = 0; i < 5; i++) {
			inn.oneDay();
		}

		//access a list of items, get the quality of the one set
		items = inn.getItems();
		quality = items.get(0).getQuality();

		//assert quality has increased by 15 and sellIn has decreased by 5
		assertEquals("Failed quality for Backstage Passes", 50, quality);
		assertEquals("Failed sellIn for Backstage Passes", 0, items.get(0).getSellIn());

		//Simulate 1 more day
		inn.oneDay();

		//access a list of items, get the quality of the one set
		items = inn.getItems();
		quality = items.get(0).getQuality();

		//Assert quality has dropped to 0 as sellIn has reached 0
		assertEquals("Failed quality for Backstage Passes", 0, quality);
		
		//Assert quality doesn't go above 50 with any sellIn value
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49));
		inn.oneDay();
		items = inn.getItems();
		quality = items.get(1).getQuality();
		assertEquals("Failed quality for Backstage Passes", 50, quality);
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48));
		inn.oneDay();
		items = inn.getItems();
		quality = items.get(2).getQuality();
		assertEquals("Failed quality for Backstage Passes", 50, quality);
		
	}

	@Test
	public void testMain() {
		//Run the main method
		GildedRose.main(null);
		//Assert that the main method runs without errors
		assertTrue(true);
	}

	@Test
	public void testLoop() {
		//Test the loop in the updateQuality method
		GildedRose inn = new GildedRose();
		//Skip the loop completely
		inn.oneDay();
		//Assert that items list is empty
		assertEquals("Failed loop in updateQuality", 0, inn.getItems().size());
		//Add an item and simulate one day
		inn.setItem(new Item("Test", 10, 10));
		inn.oneDay();
		//Assert that the loop has run once
		assertEquals("Failed loop in updateQuality", 9, inn.getItems().get(0).getQuality());
		//Add another item and simulate one day
		inn.setItem(new Item("Test", 10, 10));
		inn.oneDay();
		//Assert that the loop has run twice
		assertEquals("Failed loop in updateQuality", 9, inn.getItems().get(1).getQuality());
		//Add 100 items and simulate one day
		for (int i = 0; i < 100; i++) {
			inn.setItem(new Item("Test", 10, 10));
		}
		inn.oneDay();
		//Assert that the loop has run 102 times
		assertEquals("Failed loop in updateQuality", 9, inn.getItems().get(101).getQuality());
	}
}
