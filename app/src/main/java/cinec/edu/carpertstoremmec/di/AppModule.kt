package cinec.edu.carpertstoremmec.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import cinec.edu.carpertstoremmec.firebase.FirebaseCommon
import cinec.edu.carpertstoremmec.util.Constants.INTRODUCTION_SP
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

//This will specify the life on dependencies in this module
//singletoncomponent-> will use all the components inside this module as long as the app is alive
@InstallIn(SingletonComponent::class)

object AppModule {

    //To declare a new dependency in a module.
    @Provides

    //To create one instance throughout the whole app
    @Singleton

    //when inside the view model, when dagger hilt sees that, it will go to module files
    //and it will check we have this dependency
    //This will take it and inject in the constructor in registerViewModel
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    //now we need to give dagger hilt some privileges in order dagger hilt to create
    //classes and access to our context for the new application file is created

    @Provides
    @Singleton
    fun provideFireStoreDatabase() = Firebase.firestore

    @Provides
    fun provideIntroductionSP(
        application: Application
    ) = application.getSharedPreferences(INTRODUCTION_SP, MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesFirebaseCommon(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ) = FirebaseCommon(firestore, firebaseAuth)


    @Provides
    @Singleton
    fun provideStorage() = FirebaseStorage.getInstance().reference


}