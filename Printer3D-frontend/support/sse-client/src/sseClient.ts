import EventSource from 'eventsource';

// URL of the SSE endpoint
const sseUrl = 'http://localhost:5120/sse/v1/events';

// Create an EventSource instance
const eventSource = new EventSource(sseUrl);

// Listen for messages from the server
eventSource.onmessage = (event:string) => {
    console.log('Received event:', event);
};

// Handle errors
eventSource.onerror = (error) => {
    console.error('Error occurred:', error);
};

// Optionally, listen for specific event types
eventSource.addEventListener('customEvent', (event) => {
    console.log('Received custom event:', event);
});
