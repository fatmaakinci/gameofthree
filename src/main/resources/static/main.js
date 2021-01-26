var stompClient = null;
var username = null;
var gameId = null;

var usernamePage = document.querySelector('#username-page');
var usernameForm = document.querySelector('#usernameForm');
var gamePage = document.querySelector('#game-page');
var connectingElement = document.querySelector('.connecting');

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if (username) {
        usernamePage.classList.add('hidden');
        gamePage.classList.remove('hidden');

        var socket = new SockJS('/game-ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {

    stompClient.subscribe('/queue/message/'+username, onUserMessageReceived);

    // Join the game
    stompClient.send('/game/join/' + username, {});

    //connectingElement.classList.add('hidden');
}

function onUserMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    connectingElement.textContent=message;
}

function onError() {
    connectingElement.textContent = 'Could not connect to Game server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

usernameForm.addEventListener('submit', connect, true);
