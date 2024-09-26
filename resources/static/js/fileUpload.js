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
        priceInput.disabled = true;
        priceInput.value = '';
    } else {
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

function submitForm1() {
  let gemstoneTypeValue = document.getElementById("gemstoneType1").value;

  document.getElementById("searchForm1").action = "/products?page=1&keyWords=" + gemstoneTypeValue;

  document.getElementById("searchForm1").submit();
}

function submitForm2() {
    let gemstoneTypeValue = document.getElementById("gemstoneType2").value;

    document.getElementById("searchForm2").action = "/products?page=1&keyWords=" + gemstoneTypeValue;

    document.getElementById("searchForm2").submit();
}
