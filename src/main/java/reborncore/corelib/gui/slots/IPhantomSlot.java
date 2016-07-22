/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 * <p/>
 * BuildCraft is distributed under the terms of the Minecraft Mod Public License 1.0, or MMPL. Please check the contents
 * of the license located in http://www.mod-buildcraft.com/MMPL-1.0.txt */
package reborncore.corelib.gui.slots;

/* Phantom Slots don't "use" items, they are used for filters and various other logic slots. */
public interface IPhantomSlot {
    boolean canAdjust();
}