package com.example.testtictactoe

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),BoardCellAdapter.CellClickListenter {


    private var boardSize = 3
    private var board = arrayOfNulls<String>(boardSize * boardSize)
    private lateinit var boardCellAdapter : BoardCellAdapter
    private var currentPlayer = "X"
    private lateinit var  buttonGridChange :Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resetButton = findViewById<Button>(R.id.button_ResetGame)
        resetButton.setOnClickListener { resetGame() }

        buttonGridChange = findViewById<Button>(R.id.button_Gridchange)
        buttonGridChange.setOnClickListener { changeBoardSize() }
        initializeBoard()
        setupRecyclerView()

    }

    private fun initializeBoard() {
        for (i in board.indices){
            board[i] = ""
        }
    }

   private fun setupRecyclerView(){
       val playerBoardRV = findViewById<RecyclerView>(R.id.Rv_playerboard)
       val layoutManager = GridLayoutManager(this,boardSize)
       boardCellAdapter = BoardCellAdapter(boardSize,this)
       playerBoardRV.layoutManager = layoutManager
       playerBoardRV.adapter = boardCellAdapter

   }

    override fun onCellClick(position: Int) {
        if (board[position].isNullOrEmpty()){
           board[position] = currentPlayer
            boardCellAdapter.setBoardData(board)
            if (checkWin(currentPlayer)){
                showWinMessage()
            }else if (isBoardFull()){
                showTieMessage()
            }else{
                switchPlayer()
                makeComputerMove()
            }


        }
    }

    private fun makeComputerMove(){
        val emptyCells = mutableListOf<Int>()

        for (i in board.indices){
            if (board[i].isNullOrEmpty()){
                emptyCells.add(i)
            }
        }

        if (emptyCells.isNullOrEmpty()){
            val randomIndex = (0 until emptyCells.size).random()
            val position = emptyCells[randomIndex]
            board[position] = currentPlayer
            boardCellAdapter.setBoardData(board)
            if (checkWin(currentPlayer)){
                showWinMessage()
            }else if (isBoardFull()){
                showTieMessage()
            }
            switchPlayer()

        }
    }

    private fun switchPlayer(){
        currentPlayer = if (currentPlayer == "X") "0" else "X"
    }

    private fun checkWin(player : String) : Boolean{
        val winCombinations = mutableListOf<IntArray>()

        for (i in 0 until boardSize){
            val row = IntArray(boardSize){ row -> (i * boardSize) + row}
            winCombinations.add(row)
        }
        for (i in 0 until boardSize){
            val column = IntArray(boardSize){ column -> (column * boardSize) + i}
            winCombinations.add(column)
        }
        val diagonal1 = IntArray(boardSize){i -> (i * boardSize) + i}
        val diagonal2 = IntArray(boardSize){i ->((i+1) * boardSize) - (i+1)  }
        winCombinations.add(diagonal1)
        winCombinations.add(diagonal2)
        for (combinations in winCombinations){
            if (combinations.all { board[it] == player }){
                return true
            }
        }
        return false
    }

    private fun showWinMessage() {
        val message = "Player $currentPlayer Wins!"
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun showTieMessage() {
        val message = "It's A Tie"
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun isBoardFull() : Boolean {
        for (cell in board){
            if (cell.isNullOrEmpty()){
                return false
            }
        }
        return true
    }

    private fun resetGame(){
        initializeBoard()
        boardCellAdapter.setBoardData(board)
        currentPlayer = "X"
        Toast.makeText(this,"Game Reset ",Toast.LENGTH_LONG).show()
    }

    private fun changeBoardSize(){
        if (boardSize == 3){
            boardSize++
            board = arrayOfNulls(boardSize * boardSize)
            buttonGridChange.text = "GRID 4X4 (CLICK TO CHANGE)"
            initializeBoard()
            setupRecyclerView()

        }else if (boardSize == 4){
            boardSize++
            board = arrayOfNulls(boardSize * boardSize)
            buttonGridChange.text = "GRID 5X5 (CLICK TO CHANGE)"
            initializeBoard()
            setupRecyclerView()
        }else{
            boardSize = 3
            board = arrayOfNulls(boardSize * boardSize)
            buttonGridChange.text = "GRID 3X3 (CLICK TO CHANGE)"
            initializeBoard()
            setupRecyclerView()
        }

    }



}