/*
document.addEventListener("DOMContentLoaded", function() {
    var form = document.getElementById("linkRequestForm");
    var messageBar = document.getElementById("messageBar");
    var goBackButton = document.getElementById("goBackButton");

    form.addEventListener("submit", function(event) {
        event.preventDefault();
        var formData = new FormData(form);
        var jsonObject = {};
        formData.forEach(function(value, key) {
            jsonObject[key] = value;
        });
        fetch('/api/v1/share/new', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonObject)
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Something went wrong');
            }
        }).then(data => {
            console.log(data);
            // Show success message
            messageBar.textContent = "Successfully Shared";
            messageBar.className = "message-bar success";
            messageBar.style.display = "block";

            // Disable the submit button
            var submitButton = form.querySelector('button[type="submit"]');
            submitButton.disabled = true;

            // Show go back button
            goBackButton.style.display = "block";
        }).catch(error => {
            console.error('Error:', error);
            // Error actions here
        });
    });
});
*/

document.addEventListener("DOMContentLoaded", function() {
    var form = document.getElementById("linkRequestForm");
    var messageBar = document.getElementById("messageBar");
    var goBackButton = document.getElementById("goBackButton");

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        var formData = new FormData(form);
        var jsonObject = {};
        formData.forEach(function(value, key) {
            jsonObject[key] = value;
        });

        // Disable all form fields and the submit button visually and functionally
        Array.from(form.elements).forEach(function(element) {
            element.disabled = true;
            element.classList.add('disabled'); // Add a 'disabled' class for styling
        });

        fetch('/api/v1/share/new', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonObject)
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Something went wrong');
                }
            })
            .then(data => {
                messageBar.textContent = "Successfully Shared";
                messageBar.className = "message-bar success";
                messageBar.style.display = "block";
                goBackButton.style.display = "block";
            })
            .catch(error => {
                console.error('Error:', error);
                // Re-enable all form fields if there is an error
                Array.from(form.elements).forEach(function(element) {
                    element.disabled = false;
                    element.classList.remove('disabled'); // Remove the 'disabled' class
                });
            });
    });
});



