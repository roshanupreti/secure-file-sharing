/*
import {generateDeviceFingerprint} from './fingerprint-utils.js';

async function submitData() {
    var pin = document.getElementById('pin').value;
    var fingerprint = await generateDeviceFingerprint();

    // Extract the ID from the URL
    var pathSegments = window.location.pathname.split('/');
    var idIndex = pathSegments.indexOf('share') + 1;
    var id = pathSegments[idIndex];

    // Construct the URL
    var url = '/api/v1/share/' + encodeURIComponent(id);

    // Send a POST request with the PIN and fingerprint in the request body
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({pin: pin, clientFingerPrint: fingerprint})
    })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else if (!response.ok) {
                return response.json().then(data => {
                    throw new Error(data.message || 'An error occurred');
                });
            }
            return response.json();
        })
        .then(data => {
            /!*console.log('Success:', data);
            alert('PIN verification successful.');
            // Redirect or update UI as necessary*!/
            // Display the success message
            const successMessageElement = document.getElementById('successMessage');
            successMessageElement.textContent = 'PIN verification successful.';
            console.log('Success: ', data);
        })
        .catch(error => {
            /!*console.error('Error:', error);
            alert(error.message);*!/
            // Display the error message from the server
            const errorMessageElement = document.getElementById('errorMessage');
            errorMessageElement.textContent = error.message;
        });
}

document.addEventListener('DOMContentLoaded', () => {
    var button = document.getElementById('submitPinButton');
    if (button) {
        button.addEventListener('click', submitData);
    }
});
*/

/*
import { generateDeviceFingerprint } from './fingerprint-utils.js';

// Initialize the incorrect attempts counter
let incorrectAttempts = 0;

async function submitData() {
    var pin = document.getElementById('pin').value;
    var fingerprint = await generateDeviceFingerprint();

    // Extract the ID from the URL
    var pathSegments = window.location.pathname.split('/');
    var idIndex = pathSegments.indexOf('share') + 1;
    var id = pathSegments[idIndex];

    // Construct the URL
    var url = '/api/v1/share/' + encodeURIComponent(id);

    // Send a POST request with the PIN and fingerprint in the request body
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ pin: pin, clientFingerPrint: fingerprint })
    })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else if (!response.ok) {
                return response.json().then(data => {
                    // Increment the incorrect attempts counter
                    incorrectAttempts++;

                    // Check if the user has exceeded the maximum attempts
                    if (incorrectAttempts >= 3) {
                        // Call the API to invalidate the resource
                        invalidateResource();
                    }

                    throw new Error(data.message || 'An error occurred');
                });
            }
            return response.json();
        })
        .then(data => {
            // Reset the incorrect attempts counter on success
            incorrectAttempts = 0;

            // Display the success message
            const successMessageElement = document.getElementById('successMessage');
            successMessageElement.textContent = 'PIN verification successful.';
            console.log('Success: ', data);
        })
        .catch(error => {
            // Display the error message from the server
            const errorMessageElement = document.getElementById('errorMessage');
            errorMessageElement.textContent = error.message;
        });
}

function invalidateResource() {
    // Implement the logic to call the API and invalidate the resource
    // This is where you should handle invalidation of the resource
    console.log('Resource invalidated due to too many incorrect attempts');
}

document.addEventListener('DOMContentLoaded', () => {
    var button = document.getElementById('submitPinButton');
    if (button) {
        button.addEventListener('click', submitData);
    }
});
*/

/*import { generateDeviceFingerprint } from './fingerprint-utils.js';

let remainingAttempts = 3; // Set the initial number of attempts

async function submitData() {
    if (remainingAttempts <= 0) {
        // No more attempts left, do nothing
        return;
    }

    var pin = document.getElementById('pin').value;
    var fingerprint = await generateDeviceFingerprint();

    // Extract the ID from the URL
    var pathSegments = window.location.pathname.split('/');
    var idIndex = pathSegments.indexOf('share') + 1;
    var id = pathSegments[idIndex];

    // Construct the URL
    var url = '/api/v1/share/' + encodeURIComponent(id);

    // Send a POST request with the PIN and fingerprint in the request body
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ pin: pin, clientFingerPrint: fingerprint })
    })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else if (!response.ok) {
                return response.json().then(data => {
                    // Decrement the remaining attempts counter
                    remainingAttempts--;

                    // Display the remaining attempts as an error message
                    const errorMessageElement = document.getElementById('errorMessage');
                    errorMessageElement.textContent = `Incorrect PIN. Attempts left: ${remainingAttempts}`;

                    // Check if the user has exceeded the maximum attempts
                    if (remainingAttempts <= 0) {
                        // Disable the submit button
                        document.getElementById('submitPinButton').disabled = true;
                        // Call the API to invalidate the resource
                        invalidateResource();
                    }
                });
            }
            return response.json();
        })
        .then(data => {
            // Reset the remaining attempts counter on success
            remainingAttempts = 3;

            // Display the success message
            const successMessageElement = document.getElementById('successMessage');
            successMessageElement.textContent = 'PIN verification successful.';
            console.log('Success: ', data);
        })
        .catch(error => {
            // Display the error message from the server
            const errorMessageElement = document.getElementById('errorMessage');
            errorMessageElement.textContent = error.message;
        });
}

function invalidateResource() {
    // Implement the logic to call the API and invalidate the resource
    // This is where you should handle invalidation of the resource
    console.log('Resource invalidated due to too many incorrect attempts');
}

document.addEventListener('DOMContentLoaded', () => {
    var button = document.getElementById('submitPinButton');
    if (button) {
        button.addEventListener('click', submitData);
    }
});*/

import { generateDeviceFingerprint } from './fingerprint-utils.js';

let remainingAttempts = 3; // Set the initial number of attempts

document.addEventListener('DOMContentLoaded', () => {
    const pinForm = document.getElementById('pinForm');
    if (pinForm) {
        pinForm.addEventListener('submit', async (event) => {
            event.preventDefault(); // Prevent the form from submitting
            // ... rest of your submitData function ...
            const errorMessageElement = document.getElementById('errorMessage');
            const successMessageElement = document.getElementById('successMessage');
            const submitPinButton = document.getElementById('submitPinButton');

            // Clear any previous messages
            errorMessageElement.textContent = '';
            successMessageElement.textContent = '';

            if (remainingAttempts <= 0) {
                errorMessageElement.textContent = 'No attempts left.';
                return;
            }

            const pin = document.getElementById('pin').value;
            const fingerprint = await generateDeviceFingerprint();

            const pathSegments = window.location.pathname.split('/');
            const idIndex = pathSegments.indexOf('share') + 1;
            const id = pathSegments[idIndex];
            const url = '/api/v1/share/' + encodeURIComponent(id);

            try {
                const response = await fetch(url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ pin: pin, clientFingerPrint: fingerprint })
                });

                if (response.redirected) {
                    window.location.href = response.url;
                    return;
                }

                const data = await response.json();

                if (!response.ok) {
                    // Decrement the remaining attempts counter
                    remainingAttempts--;
                    errorMessageElement.textContent = `Incorrect PIN. Attempts left: ${remainingAttempts}`;
                    // Check if the user has exceeded the maximum attempts
                    if (remainingAttempts <= 0) {
                        submitPinButton.disabled = true; // Disable the submit button
                        document.getElementById('pinForm').disabled = true; // Disable the form
                        invalidateResource(); // Invalidate the resource
                    }
                } else {
                    // Reset the remaining attempts counter on success
                    remainingAttempts = 3;
                    successMessageElement.textContent = 'PIN verification successful.';
                    console.log('Success:', data);
                }
            } catch (error) {
                // Display the error message from the server
                errorMessageElement.textContent = `Error: ${error.message}`;
            }
        });
    }
});

/*async function submitData() {
    const errorMessageElement = document.getElementById('errorMessage');
    const successMessageElement = document.getElementById('successMessage');
    const submitPinButton = document.getElementById('submitPinButton');

    // Clear any previous messages
    errorMessageElement.textContent = '';
    successMessageElement.textContent = '';

    if (remainingAttempts <= 0) {
        errorMessageElement.textContent = 'No attempts left.';
        return;
    }

    const pin = document.getElementById('pin').value;
    const fingerprint = await generateDeviceFingerprint();

    const pathSegments = window.location.pathname.split('/');
    const idIndex = pathSegments.indexOf('share') + 1;
    const id = pathSegments[idIndex];
    const url = '/api/v1/share/' + encodeURIComponent(id);

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ pin: pin, clientFingerPrint: fingerprint })
        });

        if (response.redirected) {
            window.location.href = response.url;
            return;
        }

        const data = await response.json();

        if (!response.ok) {
            // Decrement the remaining attempts counter
            remainingAttempts--;
            errorMessageElement.textContent = `Incorrect PIN. Attempts left: ${remainingAttempts}`;
            // Check if the user has exceeded the maximum attempts
            if (remainingAttempts <= 0) {
                submitPinButton.disabled = true; // Disable the submit button
                document.getElementById('pinForm').disabled = true; // Disable the form
                invalidateResource(); // Invalidate the resource
            }
        } else {
            // Reset the remaining attempts counter on success
            remainingAttempts = 3;
            successMessageElement.textContent = 'PIN verification successful.';
            console.log('Success:', data);
        }
    } catch (error) {
        // Display the error message from the server
        errorMessageElement.textContent = `Error: ${error.message}`;
    }
}*/

function invalidateResource() {
    // Implement the logic to call the API and invalidate the resource
    console.log('Resource invalidated due to too many incorrect attempts');
}

document.addEventListener('DOMContentLoaded', () => {
    const button = document.getElementById('submitPinButton');
    if (button) {
        button.addEventListener('click', submitData);
    }
});


