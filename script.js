let boardMetaData = {
  letters: "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
  numRows: 10,
  numCols: 10,
  url: 'http://ec2-3-15-213-173.us-east-2.compute.amazonaws.com:8080',
  //url: 'http://localhost:8080',
  gameId: null,
  board: null,
  status: null
}

function calculateScore() {
  let hits = 0; let attacks = 0;
  for (let key of Object.keys(boardMetaData.board)) {
    if (boardMetaData.board[key] === "hit") {
      hits++;
      attacks++;
    } else if (boardMetaData.board[key] === "miss") {
      attacks++;
    }
  }
  return { hits, attacks };
}

function drawBoard(numrows, numcols) {
  let table = [];
  
  table.push('<tr>')
  table.push('<th class="py-2 px-3"></th>');
  for (let j = 0; j < numcols; j++) {
    table.push(`<th class="border py-2 px-3">${j+1}</th>`)
  }
  table.push('</tr>');

  for (let i = 0; i < numrows; i++) {
    table.push('<tr>')
    table.push(`<th class="border py-2 px-3">${boardMetaData.letters[i]}</th>`);
    for (let j = 0; j < numcols; j++) {
      let square = boardMetaData.letters[i] + `${j+1}`;
      if (boardMetaData.board[square] === "unattacked") {
        table.push(`<td class="border bg-info py-2 px-3" onclick="makeMove('${square}')"> </td>`)
      } else if (boardMetaData.board[square] === "miss") {
        table.push(`<td class="border bg-warning py-2 px-3" onclick="makeMove('${square}')"> </td>`)
      } else {
        table.push(`<td class="border bg-danger py-2 px-3" onclick="makeMove('${square}')"> </td>`)
      }
    }
    table.push('</tr>')
  }

  $("#board").html(table);

  if (boardMetaData.status === 'playing') {
    let score = calculateScore();
    $('#score').html(`<h4>${score.hits}/${score.attacks}</h4>`);
  } else {
    let score = calculateScore();
    $('#score').html(`<h4>${score.hits}/${score.attacks}</h4>`);
    $('#msc').html('<button class="btn btn-light mx-2" onclick="newGameClick()">New Game</button>');
  }
}

function randomString() {
  let numchars = Math.floor(Math.random() * 10) + 11;
  let str = "";
  for (let i = 0; i < numchars; i++) {
    let index = Math.floor(Math.random() * boardMetaData.letters.length);
    str += boardMetaData.letters[index];
  }
  return str;
}

function newGame() {
  console.log("Getting Game");
  boardMetaData.gameId = randomString();
  let fullUrl = `${boardMetaData.url}/game/${boardMetaData.gameId}`;
  fetch(fullUrl, { 
    method: 'POST', 
    mode: 'cors',

  })
    .then((resp) => resp.json())
    .then((board) => {
      console.log("Got Game");
      boardMetaData.board = board;
      boardMetaData.status = "playing";
      drawBoard(boardMetaData.numRows, boardMetaData.numCols);
    })
}

function deleteGame() {
  console.log("Deleting Game");
  let fullUrl = `${boardMetaData.url}/game/${boardMetaData.gameId}`;
  fetch(fullUrl, { method: 'DELETE', mode: 'cors' })
    .then((resp) => resp.text())
    .then((msg) => { console.log(msg); })
}

function newGameClick() {
  deleteGame();
  newGame();
}

function makeMove(move) {
  if (boardMetaData.status === "over") { return; }
  console.log("Updating Game");
  let firstUrl = `${boardMetaData.url}/game/${boardMetaData.gameId}/${move}`;
  let secondUrl = `${boardMetaData.url}/game/${boardMetaData.gameId}/status`;
  fetch(firstUrl, { method: 'PUT', mode: 'cors' }).then((resp) => resp.json())
    .then((board) => {
      console.log("Got Updated Game");
      boardMetaData.board = board; 
      fetch(secondUrl, { method: 'GET', mode: 'cors' }).then((resp) => resp.text())
        .then((status) => {
          console.log("Got Updated Game Status: " + status);
          boardMetaData.status = status;
          drawBoard(boardMetaData.numRows, boardMetaData.numCols);
        });
    })
}

window.onload = newGame;

window.onunload = deleteGame;

