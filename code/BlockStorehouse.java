package code;

public class BlockStorehouse {
	BlockNod top = null;
	BlockNod now = top;

	void clear() {
		top = null;
		now = top;
	}

	void push(Block block) {
		BlockNod blockNod = new BlockNod(block);
		blockNod.next = top;
		top = blockNod;
	}

	Block pop() {
		if (now == null) {
			now = top;
			return null;
		} else {
			BlockNod blockNod = now;
			now = now.next;
			return blockNod.block;
		}
	}
}

class BlockNod {
	Block block;
	BlockNod next = null;

	BlockNod(Block block) {
		this.block = block;
	}
}
