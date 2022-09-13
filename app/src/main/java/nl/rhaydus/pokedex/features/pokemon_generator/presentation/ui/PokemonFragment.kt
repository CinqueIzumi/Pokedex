package nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel.PokemonFragmentViewModel
import kotlinx.coroutines.launch
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.databinding.FragmentPokemonBinding

class PokemonFragment : Fragment() {
    private val vm: PokemonFragmentViewModel by viewModels()
    private val binding: FragmentPokemonBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.getCurrentPokemon().observe(this) { pokemon ->
            binding.tvName.text = pokemon.name
            binding.tvId.text = pokemon.id.toString()
        }

        // Initialize a random user on boot
        lifecycleScope.launch {
            vm.loadRandomPokemon()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRandomize.setOnClickListener {
            lifecycleScope.launch {
                vm.loadRandomPokemon()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon, container, false)
    }
}