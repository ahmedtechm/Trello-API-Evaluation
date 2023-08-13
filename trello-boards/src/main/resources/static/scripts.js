document.addEventListener("DOMContentLoaded", async function() {

const host = window.location.host;
const BASE_URL = "http://" + host + ":8080";



document.addEventListener("DOMContentLoaded", async function() {
  // Fetch and update the board title during initial page load
  try {
    const response = await fetch(`${BASE_URL}/api/boards`);
    const data = await response.json();
    const boardTitle = data[0].name;
    document.getElementById("boardTitle").textContent = boardTitle;

    // Fetch and populate all cards
    const cardsResponse = await fetch(`${BASE_URL}/api/boards/1/cards`);
    const cardsData = await cardsResponse.json();
    cardsData.forEach(cardData => {
      fetchCard(cardData.card_id, cardData.title, cardData.description, cardData.section);
    });
  } catch (error) {
    console.error('Error fetching board and card data:', error);
  }
});





var myHeaders = new Headers();
myHeaders.append("Content-Type", "application/json");



var requestOptions = {
  method: 'GET',
  headers: myHeaders,
  redirect: 'follow'
};

fetch(`${BASE_URL}/api/boards/1/cards`, requestOptions)
  .then((response) => {return response.json()})
  .then((result) =>{
    console.log(result);
    result.forEach(card =>{
      fetchCard(card.card_id,card.title,card.description,card.section)



    })


  })
  .catch(error => console.log('error', error));


  function fetchCard(cardid,title,desc,section){
  // Create card container
const cardContainer = document.createElement('div');
cardContainer.className = 'card';

// Create card header
const cardHeader = document.createElement('div');
cardHeader.className = 'card-header';

// Create card ID
const cardId = document.createElement('span');
cardId.className = 'card-id';
cardId.textContent = 'ID:' +cardid;

cardHeader.appendChild(cardId);

// Create card content
const cardContent = document.createElement('div');
cardContent.className = 'card-content';

// Create card name
const cardName = document.createElement('h3');
cardName.className = 'card-name';
cardName.textContent = title;

// Create card description
const cardDescription = document.createElement('p');
cardDescription.className = 'card-description';
cardDescription.textContent = desc;

cardContent.appendChild(cardName);
cardContent.appendChild(cardDescription);

// Add header and content to card container
cardContainer.appendChild(cardHeader);
cardContainer.appendChild(cardContent);
const todo = document.getElementById("1");
const inprog = document.getElementById("2");
const done = document.getElementById("3");

if (section === 1) {
  todo.appendChild(cardContainer);
} else if (section === 2) {
  inprog.appendChild(cardContainer);
} else if (section === 3) {
  done.appendChild(cardContainer);
}
}


//---------------------------------Create Card---------------------------------//
document.getElementById("createcardbtn").addEventListener("click", async function(event) {
  event.preventDefault();

   // Fetch input values
   const cardTitle = document.getElementById('title').value;
   const cardDescription = document.getElementById('description').value;
   const cardSection = document.getElementById('section').value;

  if (!cardTitle || !cardDescription || !cardSection) {
    alert("All fields are required to create a card.");
    return;
  }

  const sectionMapping = {
    "ToDo": 1,
    "InProgress": 2,
    "Done": 3
  };
  const sectionNumber = sectionMapping[cardSection];

  const cardData = {
    title: cardTitle,
    section: sectionNumber,
    description: cardDescription
  };

  const response = await fetch(`${BASE_URL}/api/boards/1/cards`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(cardData),
  });

  if (response.ok) {
    alert("Card has been added successfully.");
    // Optionally, you can fetch the updated list of cards and refresh the display
  } else {
    alert("Failed to create card. Please try again.");
  }
});

  //---------------------------------Delete Card---------------------------------//
  // Fetch card data and populate delete card dropdown
  fetch(`${BASE_URL}/api/boards/1/cards`)
    .then((response) => response.json())
    .then((result) => {
      console.log(result); // Debugging: Log the fetched card data
      const deleteCardDropdown = document.getElementById("deleteCard");
      result.forEach(card => {
        const option = document.createElement("option");
        option.value = card.card_id; // Use the appropriate card identifier
        option.textContent = card.title; // Use the appropriate card title
        deleteCardDropdown.appendChild(option);
      });
    })
    .catch(error => console.log('Error fetching card data:', error));

  // Event listener for deleting a card
  document.getElementById("deletecardbtn").addEventListener("click", async function(event) {
    event.preventDefault();

    const selectedCardId = document.getElementById("deleteCard").value;

  if (!selectedCardId) {
    alert("Please select a card to delete.");
    return;
  }

  const confirmDelete = confirm("Are you sure you want to delete this card?");
  if (!confirmDelete) {
    return;
  }

  const response = await fetch(`${BASE_URL}/api/boards/1/cards/${selectedCardId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  if (response.ok) {
    alert("Card has been deleted successfully.");
    // Optionally, you can fetch the updated list of cards and refresh the display
  } else {
    alert("Failed to delete card. Please try again.");
  }
});



 //---------------------------------Update Card---------------------------------//
  // Fetch card data and populate update card dropdown
  fetch(`${BASE_URL}/api/boards/1/cards`)
    .then((response) => response.json())
    .then((result) => {
      console.log(result); // Debugging: Log the fetched card data
      const updateCardDropdown = document.getElementById("updateCard");
      result.forEach(card => {
        const option = document.createElement("option");
        option.value = card.card_id;
        option.textContent = card.title;
        updateCardDropdown.appendChild(option);
      });
    })
    .catch(error => console.log('Error fetching card data:', error));

  // Event listener for updating a card
  document.getElementById("updateCardForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const updateCardSelect = document.getElementById("updateCard");
    const selectedCardId = updateCardSelect.value;
    const updateTitleInput = document.getElementById("updateTitle");
    const updateDescriptionInput = document.getElementById("updateDescription");
    const updateSectionSelect = document.getElementById("updateSection");
    const selectedSection = updateSectionSelect.value; // Make sure this value is correctly retrieved

    if (!selectedCardId || !updateTitleInput.value || !updateDescriptionInput.value || !selectedSection) {
      alert("All fields are required to update a card.");
      return;
    }

    const sectionMapping = {
      "1": "ToDo",
      "2": "InProgress",
      "3": "Done"
    };

    const updatedCardData = {
      title: updateTitleInput.value,
      description: updateDescriptionInput.value,
      section: selectedSection // Use the numeric value
    };

    const response = await fetch(`${BASE_URL}/api/boards/1/cards/${selectedCardId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(updatedCardData),
    });

    if (response.ok) {
      alert("Card has been updated successfully.");
      // Optionally, you can fetch the updated list of cards and refresh the display
    } else {
      alert("Failed to update card. Please try again.");
    }
  });




  //---------------------------------Update Board Title ---------------------------------//
  document.getElementById("updateTitleButton").addEventListener("click", async function(event) {
    event.preventDefault();

    const newBoardTitle = document.getElementById("updateTitleInput").value;

    if (!newBoardTitle) {
      alert("Please enter a new board title.");
      return;
    }

    const updatedBoardData = {
      name: newBoardTitle
    };

    const response = await fetch(`${BASE_URL}/api/boards/1`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(updatedBoardData),
    });

    if (response.ok) {
      alert("Board title has been updated successfully.");
      document.getElementById("boardTitle").textContent = newBoardTitle; // Update the displayed title
    } else {
      alert("Failed to update board title. Please try again.");
    }
  });




////////////////////////////
document.addEventListener("DOMContentLoaded", function() {
  
  // Fetch and populate all cards
  fetch(`${BASE_URL}/api/boards/1/cards`)
    .then((response) => response.json())
    .then((result) => {
      console.log(result); // Debugging: Log the fetched card data
      result.forEach(card => {
        fetchCard(card.card_id, card.title, card.description, card.section); 
      });
    })
    .catch(error => console.log('Error fetching card data:', error));
});


})