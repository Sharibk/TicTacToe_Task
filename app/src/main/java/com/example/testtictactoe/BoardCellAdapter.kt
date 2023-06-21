package com.example.testtictactoe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class BoardCellAdapter(private val boardSize : Int,private val cellClickListener : CellClickListenter) :
    RecyclerView.Adapter<BoardCellAdapter.ViewHolder>() {

    private val board = arrayOfNulls<String>(boardSize * boardSize)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.playerboard_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardCellAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
       return board.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val ButtonCell : Button = itemView.findViewById(R.id.button)

        init {
            ButtonCell.setOnClickListener {
                val position = adapterPosition
                if (board[position].isNullOrEmpty()){
                    cellClickListener.onCellClick(position)
                }
            }
        }

        fun bind(position: Int){
            ButtonCell.text = board[position]
        }

    }

    fun setBoardData(boardData : Array<String?>){
        boardData.copyInto(board)
        notifyDataSetChanged()
    }

    interface CellClickListenter{
        fun onCellClick(position: Int)
    }
}