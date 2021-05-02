var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversationA0").show();
        $("#conversationA1").show();
        $("#conversationB0").show();
        $("#conversationB1").show();
    }
    else {
        $("#conversationA0").hide();
        $("#conversationA1").hide();
        $("#conversationB0").hide();
        $("#conversationB1").hide();
    }
    $("#userinfoA0").html("");
    $("#userinfoA1").html("");
    $("#userinfoB0").html("");
    $("#userinfoB1").html("");
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/stream/consumerA0', function (message) {
            showA0(JSON.parse(message.body).name);
        });
        stompClient.subscribe('/stream/consumerA1', function (message) {
            showA1(JSON.parse(message.body).name);
        });
        stompClient.subscribe('/stream/consumerB0', function (message) {
            showB0(JSON.parse(message.body).name);
        });
        stompClient.subscribe('/stream/consumerB1', function (message) {
            showB1(JSON.parse(message.body).name);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/toKafka", {}, JSON.stringify({'name': $("#name").val()}));
}

function showA0(message) {
    $("#userinfoA0").append("<tr><td>" + message + "</td></tr>");
}

function showA1(message) {
    $("#userinfoA1").append("<tr><td>" + message + "</td></tr>");
}

function showB0(message) {
    $("#userinfoB0").append("<tr><td>" + message + "</td></tr>");
}

function showB1(message) {
    $("#userinfoB1").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#sendMessage" ).click(function() { sendMessage(); });
});