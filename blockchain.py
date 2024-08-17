import hashlib
import json

class Block:
    def __init__(self, index, previous_hash, timestamp, data, bandwidth_usage):
        self.index = index
        self.previous_hash = previous_hash
        self.timestamp = timestamp
        self.data = data
        self.bandwidth_usage = bandwidth_usage
        self.hash = self.calculate_hash()

    def calculate_hash(self):
        block_string = f"{self.index}{self.previous_hash}{self.timestamp}{self.data}{self.bandwidth_usage}"
        return hashlib.sha256(block_string.encode()).hexdigest()

class Blockchain:
    def __init__(self):
        self.chain = []
        self.create_block(previous_hash='1', index=0)  # Create the genesis block

    def create_block(self, previous_hash, index, timestamp=None, data='', bandwidth_usage=0):
        block = Block(index, previous_hash, timestamp, data, bandwidth_usage)
        self.chain.append(block)
        return block

    def get_latest_block(self):
        return self.chain[-1]

    def add_block(self, block):
        self.chain.append(block)

    def to_dict(self):
        return [block.__dict__ for block in self.chain]
