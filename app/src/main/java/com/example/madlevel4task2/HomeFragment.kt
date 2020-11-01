package com.example.madlevel4task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "MadLevel4Task2"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

            gameRepository = GameRepository(requireContext())

            ivRock.setImageResource(R.drawable.rock)
            ivPaper.setImageResource(R.drawable.paper)
            ivScissors.setImageResource(R.drawable.scissors)

            ivRock.setOnClickListener {
                startGame(GameEnum.ROCK)
            }
            ivPaper.setOnClickListener {
                startGame(GameEnum.PAPER)
            }
            ivScissors.setOnClickListener {
                startGame(GameEnum.SCISSER)
            }
    }

    fun startGame(user: GameEnum) {
        val computer = computerAnswer()

        ivComputer.setImageResource(computer.element)
        ivYou.setImageResource(user.element)

        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(
                    Game(getWinner(computer, user),
                        Date(),
                        computer.element,
                        user.element
                    )
                )
            }
        }
    }

    fun computerAnswer(): GameEnum {
        return GameEnum.values().toList().shuffled().get(0)
    }

    private fun getWinner(computer: GameEnum, user: GameEnum): String {

        if (computer == GameEnum.ROCK && user == GameEnum.SCISSER ||
            computer == GameEnum.PAPER && user == GameEnum.ROCK ||
            computer == GameEnum.SCISSER && user == GameEnum.PAPER
        ) {
            mainScope.launch {
                withContext(Dispatchers.Main) {
                    tvWinner.setText(R.string.computerWins)
                }
            }

            return "Computer wins!"
        } else if (
            computer == GameEnum.ROCK && user == GameEnum.PAPER ||
            computer == GameEnum.PAPER && user == GameEnum.SCISSER ||
            computer == GameEnum.SCISSER && user == GameEnum.ROCK
        ) {
            mainScope.launch {
                withContext(Dispatchers.Main) {
                    tvWinner.setText(R.string.youWins)
                }
            }
            return "You win!"
        } else {
            mainScope.launch {
                withContext(Dispatchers.Main) {
                    tvWinner.setText(R.string.draw)
                }
            }
            return "Draw"
        }
    }
}