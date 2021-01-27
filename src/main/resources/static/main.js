var stompClient = null;
var username = null;
var gameId = null;

var messageTable = document.getElementById('messageTable').getElementsByTagName('tbody')[0];

function connect(event) {
    username = document.querySelector('#username').value.trim();

    if (username) {
        var socket = new SockJS('/game-ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onConnectionError);
    }
}

function onConnected() {

    stompClient.subscribe('/queue/message/' + username, onMessageReceived);
    stompClient.subscribe('/queue/error/' + username, onErrorMessageReceived);

    // Join the game
    stompClient.send('/game/join/' + username, {});
}

function onConnectionError() {
    alert('Could not connect to Game server. Please refresh this page to try again!');
}

function startGame() {
    stompClient.send('/game/start', {}, JSON.stringify({
        'gameId': gameId,
        'playerName': username,
    }));
}

function makeMove() {
    var choice = document.getElementsByName('additionChoice');

    var addition = null;
    var additionType = 'MANUAL';

    if (choice[3].checked) {
        additionType = 'AUTO';
    } else {
        for (i = 0; i < 3; i++) {
            if (choice[i].checked) {
                addition = choice[i].value;
            }
        }
    }

    stompClient.send('/game/move', {}, JSON.stringify({
        'gameId': gameId,
        'playerName': username,
        'additionType': additionType,
        'addition': addition,
    }));
}

function onMessageReceived(payload) {
    var resultJson = JSON.parse(payload.body);

    if (!gameId) {
        gameId = resultJson.gameId;
        stompClient.subscribe('/topic/game/' + gameId, onMessageReceived);
    }

    var newRow = messageTable.insertRow(messageTable.rows.length);
    newRow.innerHTML = '<td>' + resultJson.message + '</td>';
}

function onErrorMessageReceived(message) {
    alert(message.body);
}
