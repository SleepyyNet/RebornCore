package reborncore.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.Constants;
import reborncore.common.tile.TileBase;

public class Inventory implements IInventory {

	public ItemStack[] contents;
	private final String name;
	private final int stackLimit;
	private TileEntity tile;
	public boolean hasChanged = false;

	public Inventory(int size, String invName, int invStackLimit, TileEntity tileEntity) {
		contents = new ItemStack[size];
		for (int i = 0; i < getSizeInventory(); i++) {
			contents[i] = ItemStack.EMPTY;
		}
		name = invName;
		stackLimit = invStackLimit;
		this.tile = tileEntity;
	}

	@Override
	public int getSizeInventory() {
		return contents.length;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : contents) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int slotId) {
		return contents[slotId];
	}

	@Override
	public ItemStack decrStackSize(int slotId, int count) {
		if (slotId < contents.length && contents[slotId] != ItemStack.EMPTY) {
			if (contents[slotId].getCount() > count) {
				ItemStack result = contents[slotId].splitStack(count);
				markDirty();
				hasChanged = true;
				return result;
			}
			ItemStack stack = contents[slotId];
			setInventorySlotContents(slotId, ItemStack.EMPTY);
			hasChanged = true;
			return stack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int slotId, ItemStack itemstack) {
		if (slotId >= contents.length) {
			return;
		}
		contents[slotId] = itemstack;

		if (itemstack != ItemStack.EMPTY && itemstack.getCount() > this.getInventoryStackLimit()) {
			itemstack.setCount(this.getInventoryStackLimit());
		}
		markDirty();
		hasChanged = true;
	}

	@Override
	public int getInventoryStackLimit() {
		return stackLimit;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	public void readFromNBT(NBTTagCompound data) {
		readFromNBT(data, "Items");
	}

	public void readFromNBT(NBTTagCompound data, String tag) {
		NBTTagList nbttaglist = data.getTagList(tag, Constants.NBT.TAG_COMPOUND);

		for (int j = 0; j < nbttaglist.tagCount(); ++j) {
			NBTTagCompound slot = nbttaglist.getCompoundTagAt(j);
			int index;
			if (slot.hasKey("index")) {
				index = slot.getInteger("index");
			} else {
				index = slot.getByte("Slot");
			}
			if (index >= 0 && index < contents.length) {
				setInventorySlotContents(index, new ItemStack(slot));
			}
		}
		hasChanged = true;
	}

	public void writeToNBT(NBTTagCompound data) {
		writeToNBT(data, "Items");
	}

	public void writeToNBT(NBTTagCompound data, String tag) {
		NBTTagList slots = new NBTTagList();
		for (byte index = 0; index < contents.length; ++index) {
			if (contents[index] != ItemStack.EMPTY && contents[index].getCount() > 0) {
				NBTTagCompound slot = new NBTTagCompound();
				slots.appendTag(slot);
				slot.setByte("Slot", index);
				contents[index].writeToNBT(slot);
			}
		}
		data.setTag(tag, slots);
	}

	public void setTile(TileEntity tileEntity) {
		tile = tileEntity;
	}

	@Override
	public ItemStack removeStackFromSlot(int slotId) {
		if (this.contents[slotId] == ItemStack.EMPTY) {
			return ItemStack.EMPTY;
		}

		ItemStack stackToTake = this.contents[slotId];
		setInventorySlotContents(slotId, ItemStack.EMPTY);
		return stackToTake;
	}

	public ItemStack[] getStacks() {
		return contents;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public void markDirty() {
		tile.markDirty();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(name);
	}

	public TileEntity getTileBase() {
		return tile;
	}
}
