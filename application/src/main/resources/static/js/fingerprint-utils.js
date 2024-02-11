async function generateDeviceFingerprint() {
    try {
        const {userAgent, language} = window.navigator;
        const {width, height} = window.screen;
        const response = await fetch('https://api.ipify.org?format=json');
        const {ip} = await response.json();

        return hashObject({
            userAgent,
            language,
            screenResolution: `${width}x${height}`,
            timezoneOffset: new Date().getTimezoneOffset(),
            ipAddress: ip
        });
    } catch (error) {
        console.error('Error fetching IP address:', error);
        return null; // Or handle the error as needed
    }
}

function hashObject(obj) {
    const stringifierObj = JSON.stringify(obj);
    return Array.from(stringifierObj).reduce((hash, char) => {
        return ((hash << 5) - hash) + char.charCodeAt(0) | 0;
    }, 0);
}

export {generateDeviceFingerprint};