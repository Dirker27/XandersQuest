package com.toastercat.xander.game.object;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameObjectTest {
    private GameObject object;

    /**
     * DEFAULT OBJECT:
     *   Centered about origin with boundaries at 1m
     *
     *   [0, 0, 0]
     *   (2, 2)
     *   >1<
     */
    @Before
    public void setUp() {
        object = new GameObject();
        object.setLocation(0, 0);
        object.setSize(2, 2);
        object.setScale(1);
    }

    @Test
    public void testDetectCollision() {
        GameObject other = new GameObject();
        other.setLocation(1, 1);
        other.setSize(2, 2);
        other.setScale(1);

        Assert.assertTrue(object.detectCollision(other));
        Assert.assertTrue(other.detectCollision(object));
    }
    @Test
    public void testDetectCollision_WholeEnvelopment() {
        GameObject other = new GameObject();
        other.setLocation(1, 1);
        other.setSize(10, 10);
        other.setScale(1);

        Assert.assertTrue(object.detectCollision(other));
        Assert.assertTrue(other.detectCollision(object));
    }

    @Test
    public void testGetBounds() {
        Assert.assertEquals(-1f, object.getLeftBound(), 0f);
        Assert.assertEquals(1f, object.getRightBound(), 0f);
        Assert.assertEquals(-1, object.getBottomBound(), 0f);
        Assert.assertEquals(1, object.getTopBound(), 0f);
    }
}
