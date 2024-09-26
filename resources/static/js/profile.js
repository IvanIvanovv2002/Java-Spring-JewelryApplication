function showWishlist() {
    document.getElementById('wishlist').style.display = 'block';
    document.querySelectorAll(' main section').forEach(selector => {
        if (selector.id !== 'wishlist') { selector.style.display = 'none'; }
    })
}

function showBasePage() {
    document.getElementById('basic-info').style.display = 'block';
    document.querySelectorAll(' main section').forEach(selector => {
        if (selector.id !== 'basic-info') { selector.style.display = 'none'; }
    })
}

function togglePasswordChanger() {
    document.getElementsByClassName('loginForm')[0].style.display = 'flex';
    document.querySelectorAll(' main section').forEach(selector => {
        if (selector.className !== 'loginForm') { selector.style.display = 'none'; }
    })
}