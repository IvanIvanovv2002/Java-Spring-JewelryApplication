function changePages(page) {
    switch (page) {
        case 'loginPage' :
            document.getElementsByClassName('registerForm').item(0).style.display = 'none'
            document.getElementsByClassName('loginForm').item(0).style.display = 'flex'
            break;
        case 'registerPage' :
            document.getElementsByClassName('registerForm').item(0).style.display = 'flex'
            document.getElementsByClassName('loginForm').item(0).style.display = 'none'
            break;
    }
}

function dismiss() {
    document.querySelector('.alert').style.display = 'none'
}

