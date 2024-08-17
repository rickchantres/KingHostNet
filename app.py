from flask import Flask, render_template, request, jsonify
from blockchain import Blockchain
from pow_bandwidth import ProofOfBandwidth

app = Flask(__name__)

# Inicializar a blockchain e o Proof of Bandwidth
blockchain = Blockchain()
proof_of_bandwidth = ProofOfBandwidth(blockchain)

@app.route('/')
def index():
    # Renderiza a página HTML e passa a blockchain para o template
    return render_template('index.html', blockchain=blockchain.chain)

@app.route('/mine', methods=['POST'])
def mine():
    # Obtém os dados do formulário
    data = request.form['data']
    
    # Minerar um novo bloco
    new_block = proof_of_bandwidth.mine_block(data)
    
    # Retorna uma resposta com detalhes do novo bloco minerado
    return jsonify({
        'index': new_block.index,
        'data': new_block.data,
        'bandwidth_usage': new_block.bandwidth_usage,
        'hash': new_block.hash
    })

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
