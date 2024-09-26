function toggleChangePrice() {
    document.querySelector('#change-stock').style.display = 'none';
    document.querySelector('#change-description').style.display = 'none';
    document.querySelector('#change-price').style.display = 'flex';
    document.querySelector('#change-name').style.display = 'none';
}

function toggleChangeDescription() {
    document.querySelector('#change-stock').style.display = 'none';
    document.querySelector('#change-description').style.display = 'flex';
    document.querySelector('#change-price').style.display = 'none';
    document.querySelector('#change-name').style.display = 'none';
}

function toggleChangeName() {
    document.querySelector('#change-stock').style.display = 'none';
    document.querySelector('#change-description').style.display = 'none';
    document.querySelector('#change-price').style.display = 'none';
    document.querySelector('#change-name').style.display = 'flex';
}

function toggleStockChange() {
    document.querySelector('#change-stock').style.display = 'flex';
    document.querySelector('#change-description').style.display = 'none';
    document.querySelector('#change-price').style.display = 'none';
    document.querySelector('#change-name').style.display = 'none';
}
