function toggleStaticFileUploader() {
    let container = document.querySelector('.container1');
    container.style.display = (container.style.display === 'none' || container.style.display === '') ? 'flex' : 'none';
}

function dismissStatic() {
    let container = document.querySelector('.container1');
    container.style.display = 'none'
}

function toggleDynamicFileUploader() {
    let container = document.querySelector('.container2');
    container.style.display = (container.style.display === 'none' || container.style.display === '') ? 'flex' : 'none';
}
function dismissDynamic() {
    let container = document.querySelector('.container2');
    container.style.display = 'none'
}

function togglePriceInput() {
    let priceInput = document.getElementById("price");
    let inStockRadio = document.getElementById("inStock");

    if (inStockRadio.checked) {
        // If "inStock" is checked, disable the price input and clear its value
        priceInput.disabled = true;
        priceInput.value = '';
    } else {
        // If "inStock" is unchecked, enable the price input
        priceInput.disabled = false;
    }
}

function toggleGemstoneOriginInput() {
    let gemstoneInputs = document.getElementsByClassName("gemstone");
    let jewelrySelect = document.getElementById("jewelry");

    if (jewelrySelect.value === "Gemstone") {
        Array.from(gemstoneInputs).forEach( input => { input.style.display = 'block'; });
    } else {
        Array.from(gemstoneInputs).forEach(input => { input.style.display = 'none'; });
    }
}

function submitForm() {
  let gemstoneTypeValue = document.getElementById("keyWords").value;

  document.getElementById("searchForm").action = "/products?page=1&keyWords=" + gemstoneTypeValue;

  document.getElementById("searchForm").submit();
}