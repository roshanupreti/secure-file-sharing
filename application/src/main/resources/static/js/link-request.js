document.addEventListener("DOMContentLoaded", function() {
    var form = document.getElementById("linkRequestForm");
    var messageBar = document.getElementById("messageBar");
    var goBackButton = document.getElementById("goBackButton");

    form.addEventListener("submit", function(event) {
        event.preventDefault();
        document.addEventListener("DOMContentLoaded", function() {
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
                    // Disable form and show go back button
                    form.classList.add("disabled");
                    form.querySelectorAll("input, textarea, button").forEach(el => el.disabled = true);
                    goBackButton.style.display = "block";
                }).catch(error => {
                    console.error('Error:', error);
                    // Show error message
                    messageBar.textContent = "An error occurred. Please try again.";
                    messageBar.className = "message-bar error";
                    messageBar.style.display = "block";
                });
            });
        });
    });
});
