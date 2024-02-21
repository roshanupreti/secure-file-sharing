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
                        invalidateResource(id); // Invalidate the resource
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

function invalidateResource(id) {
    const url = `/api/v1/share/${encodeURIComponent(id)}/invalidate`;

    fetch(url, {
        method: 'POST', // Using POST method
        headers: {
            'Content-Type': 'application/json', // Even with no payload, specifying content type is good practice
        },
    })
        .then(response => {
            if (response.ok) {
                console.log('Resource invalidated successfully');
                // Additional logic upon successful invalidation, if needed
            } else {
                console.error('Failed to invalidate resource');
                // Handle server errors or unsuccessful invalidation
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });

    console.log('Resource invalidated due to too many incorrect attempts');
}


