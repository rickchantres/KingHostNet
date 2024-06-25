const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());

const db = mysql.createConnection({
    host: '45.236.162.3',  // Atualize para o IP correto do seu banco de dados MySQL
    user: 'kinghost', // Atualize para seu usuário MySQL
    password: 'S#adm#rick#0_king_host', // Atualize para sua senha MySQL
    database: 'kinghost'
});

db.connect(err => {
    if (err) throw err;
    console.log('MySQL connected...');
});

// Função para gerar uma porta aleatória entre 1000 e 50000
const generateRandomPort = () => {
    return Math.floor(Math.random() * (50000 - 1000 + 1)) + 1000;
};

// Endpoint para obter dispositivos ativos
app.get('/devices', (req, res) => {
    const sql = 'SELECT * FROM devices WHERE status = "active" ORDER BY speed DESC LIMIT 20';
    db.query(sql, (err, result) => {
        if (err) throw err;
        res.json(result);
    });
});

// Endpoint para adicionar/atualizar dispositivo
app.post('/devices', (req, res) => {
    const device = req.body;
    const port = generateRandomPort();
    const sql = 'INSERT INTO devices (device_id, status, speed, port) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE status=?, speed=?, port=?';
    db.query(sql, [device.device_id, device.status, device.speed, port, device.status, device.speed, port], (err, result) => {
        if (err) throw err;
        res.send('Device updated...');
    });
});

// Endpoint para adicionar uma requisição
app.post('/requests', (req, res) => {
    const request = req.body;
    const sql = 'INSERT INTO requests (url, status) VALUES (?, ?)';
    db.query(sql, [request.url, request.status], (err, result) => {
        if (err) throw err;
        res.send('Request added...');
    });
});

// Endpoint para verificar a versão do aplicativo
app.get('/app_version', (req, res) => {
    const sql = 'SELECT * FROM app_version ORDER BY id DESC LIMIT 1';
    db.query(sql, (err, result) => {
        if (err) throw err;
        res.json(result[0]);
    });
});

app.listen(3000, () => {
    console.log('Server started on port 3000...');
});