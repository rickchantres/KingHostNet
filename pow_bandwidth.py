import time
import hashlib

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

class ProofOfBandwidth:
    def __init__(self, blockchain):
        self.blockchain = blockchain

    def mine_block(self, data):
        last_block = self.blockchain.get_latest_block()  # Atualize para usar get_latest_block
        index = last_block.index + 1
        timestamp = time.time()
        bandwidth_usage = self.calculate_bandwidth_usage()
        new_block = Block(index, last_block.hash, timestamp, data, bandwidth_usage)
        self.blockchain.add_block(new_block)
        return new_block

    def calculate_bandwidth_usage(self):
        # Implementação para calcular a largura de banda utilizada
        return 10  # Exemplo de valor fixo
