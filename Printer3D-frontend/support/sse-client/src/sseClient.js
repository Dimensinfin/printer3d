"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var eventsource_1 = require("eventsource");
// URL of the SSE endpoint
var sseUrl = 'http://localhost:5120/sse/v1/events';
// Create an EventSource instance
var eventSource = new eventsource_1.default(sseUrl);
// Listen for messages from the server
eventSource.onmessage = function (event) {
    console.log('Received event:', event);
};
// Handle errors
eventSource.onerror = function (error) {
    console.error('Error occurred:', error);
};
// Optionally, listen for specific event types
eventSource.addEventListener('customEvent', function (event) {
    console.log('Received custom event:', event);
});
