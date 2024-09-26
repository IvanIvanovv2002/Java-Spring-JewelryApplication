document.addEventListener('DOMContentLoaded', function() {
    initializeItems();
});

let counterMap = new Map();

$(document).on('click', '.number-spinner button', function () {
    let btn = $(this),
        oldValue = btn.closest('.number-spinner').find('input').val().trim(),
        newVal = 0;

    if (btn.attr('data-dir') === 'up') {
        newVal = parseInt(oldValue) + 1;
    } else {
        if (oldValue > 1) {
            newVal = parseInt(oldValue) - 1;
        } else {
            newVal = 1;
        }
    }
    btn.closest('.number-spinner').find('input').val(newVal);
});

function increasePrice(element) {
    let parent1 = element.parentNode;
    let parent2 = parent1.parentNode;
    let parent3 = parent2.parentNode;
    let parent4 = parent3.parentNode;
    let parent5 = parent4.parentNode;
    let parent6 = parent5.parentNode;

    let price = parseFloat(parent6.querySelector('#info h4').innerText);
    let itemId = parseInt(parent6.querySelector('.product-id').id);
    let totalPrice = parseFloat(document.querySelector('#totalPrice').innerText);

    let calculated = price + totalPrice;
    document.querySelector('#totalPrice').innerText = calculated.toFixed(2) + ' BGN';
    counterMap.set(itemId,counterMap.get(itemId) + 1);
}

function decreasePrice(element) {
    if (document.querySelector('#values').value === '1') { return; }

    let parent = element.parentNode;
    let parent2 = parent.parentNode;
    let parent3 = parent2.parentNode;
    let parent4 = parent3.parentNode;
    let parent5 = parent4.parentNode;
    let parent6 = parent5.parentNode;

    let price = parseFloat(parent6.querySelector('#info h4').innerText);
    let itemId = parseInt(parent6.querySelector('.product-id').id);
    let totalPrice = parseFloat(document.querySelector('#totalPrice').innerText);

    let calculated = totalPrice - price; // Adjust the total price by subtracting the item price
    document.querySelector('#totalPrice').innerText = calculated.toFixed(2) + ' BGN';
    counterMap.set(itemId, counterMap.get(itemId) - 1);
}
function initializeItems() {
    for (const element of document.getElementsByClassName('product-id')) {
        const id = parseInt(element.id);
        counterMap.set(id,1);
    }
}

function addCookieAndCalculateTotalPrice() {
    try {
        const cookieValue = JSON.stringify(Object.fromEntries(counterMap))
        const encodedCookieValue = encodeURIComponent(cookieValue);
        document.cookie = `items=${encodedCookieValue}; path=/`;

        document.querySelector('#subtotalPrice').value = parseFloat(document.querySelector('#totalPrice').innerText);

    } catch (e) {
        console.error("An error occurred while adding the cookie:", e);
    }
}


$(window).on('load', function() {
    $('.number-spinner input').val(1);
});

$(document).ready(function(){
    /*****Fixed Menu******/
    var secondaryNav = $('.cd-secondary-nav'),
        secondaryNavTopPosition = secondaryNav.offset().top;
    $(window).on('scroll', function(){
        if($(window).scrollTop() > secondaryNavTopPosition ) {
            secondaryNav.addClass('is-fixed');
        } else {
            secondaryNav.removeClass('is-fixed');
        }
    });

});