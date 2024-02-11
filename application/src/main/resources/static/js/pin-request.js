import {generateDeviceFingerprint} from './fingerprint-utils.js';

async function requestPIN(id) {
    try {
        var clientFingerPrint = await generateDeviceFingerprint();
        fetch('/api/v1/share/' + id + '/request', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({clientFingerPrint: clientFingerPrint})
        })
            .then(handleResponse)
            .catch(handleError);
    } catch (error) {
        console.error('Error generating fingerprint:', error);
        alert('Error generating fingerprint: ' + error.message);
    }
}

function handleResponse(response) {
    if (response.redirected) {
        // Follow the redirect
        window.location.href = response.url;
    } else if (response.ok) {
        // Display the success message
        const successMessageElement = document.getElementById('successMessage');
        successMessageElement.textContent = 'PIN requested successfully.';
        console.log('Request successful');
    } else {
        // Handle non-OK responses
        response.json().then(data => {
            // Display the error message from the server
            const errorMessageElement = document.getElementById('errorMessage');
            errorMessageElement.textContent = data.message;
        });
    }
}

function handleError(error) {
    console.error('Error:', error);
    alert('An error occurred: ' + error.message);
}

document.addEventListener('DOMContentLoaded', function () {
    var button = document.getElementById('requestPinButton');
    if (button) {
        button.addEventListener('click', function () {
            requestPIN(pinRequestId);
        });
    }
});



