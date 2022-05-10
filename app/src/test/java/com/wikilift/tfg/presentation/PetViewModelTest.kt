package com.wikilift.tfg.presentation

import androidx.fragment.app.viewModels
import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.local.room.AppDatabase
import com.wikilift.tfg.data.model.room.entity.PetBase
import com.wikilift.tfg.domain.PetRepo
import com.wikilift.tfg.domain.PetRepoImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class PetViewModelTest {

    @RelaxedMockK

    private lateinit var repo: PetRepo

    private lateinit var viewModel: PetViewModel

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        viewModel=PetViewModel(repo)
    }
    @Test
    fun `get empty list from data base`() = runBlocking {
        //Given
        //every time mock is called
        coEvery { repo.getAll() } returns emptyList()
        //When
        repo.getAll()

        //Then
        coVerify (exactly = 1){
            repo.getAll()
        }
    }

    @Test
    fun `get  list with data from data base`() = runBlocking {

        val myList= listOf<PetBase>(PetBase(name = "test1"),PetBase(name = "test2"),
            PetBase(name = "test3"),PetBase(name = "test4"),PetBase(name = "test5"))
        //Given
        //every time mock is called
        coEvery { repo.getAll() } returns myList
        //When

        myList.forEach{
            repo.insertPet(it)
        }
        val answer=repo.getAll()

        //Then
        coVerify (exactly = 1){
            repo.getAll()
        }
        assert(myList==answer)
    }

    @Test
    fun `insert pet to data base`() = runBlocking {

        val pet=PetBase(name = "test")
        //Given
        //every time mock is called
        coEvery { repo.insertPet(pet) } returns Unit
        //When
        repo.insertPet(pet)

        //Then
        coVerify (exactly = 1){
            repo.insertPet(pet)
        }

    }
    @Test
    fun `update pet in data base`() = runBlocking {

        val pet=PetBase(name = "test")
        //Given
        //every time mock is called
        coEvery { repo.update(pet) } returns Unit
        //When
        repo.update(pet)

        //Then
        coVerify (exactly = 1){
            repo.update(pet)
        }

    }
    @Test
    fun `delete pet to data base`() = runBlocking {

        val pet=PetBase(name = "test")
        //Given
        //every time mock is called
        coEvery { repo.deletePet(pet) } returns Unit
        //When
        repo.deletePet(pet)

        //Then
        coVerify (exactly = 1){
            repo.deletePet(pet)
        }

    }
}