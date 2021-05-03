var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#counts").show();
        $("#conversationA0").show();
        $("#conversationA1").show();
        $("#conversationB0").show();
        $("#conversationB1").show();
    }
    else {
        $("#counts").hide();
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
        stompClient.subscribe('/stream/producerCount', function (message) {
            showProducerCount(JSON.parse(message.body).count);
        });
        stompClient.subscribe('/stream/consumerA0Count', function (message) {
            showConsumerA0Count(JSON.parse(message.body).count);
        });
        stompClient.subscribe('/stream/consumerA1Count', function (message) {
            showConsumerA1Count(JSON.parse(message.body).count);
        });
        stompClient.subscribe('/stream/consumerB0Count', function (message) {
            showConsumerB0Count(JSON.parse(message.body).count);
        });
        stompClient.subscribe('/stream/consumerB1Count', function (message) {
            showConsumerB1Count(JSON.parse(message.body).count);
        });
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

function showProducerCount(message) {
    $("#producerCount").text(message);
}

function showConsumerA0Count(message) {
    $("#consumerA0Count").text(message);
}

function showConsumerA1Count(message) {
    $("#consumerA1Count").text(message);
}

function showConsumerB0Count(message) {
    $("#consumerB0Count").text(message);
}

function showConsumerB1Count(message) {
    $("#consumerB1Count").text(message);
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