package com.example.luontopeli.data.repository

import com.example.luontopeli.data.local.dao.WalkSessionDao
import com.example.luontopeli.data.local.entity.WalkSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository-luokka kävelykertojen hallintaan (Repository-suunnittelumalli).
 *
 * Toimii välittäjänä Room-tietokannan (WalkSessionDao) ja ViewModelien välillä.
 * Tarjoaa yksinkertaisen rajapinnan kävelylenkkien CRUD-operaatioihin.
 *
 * @param walkSessionDao WalkSessionDao tietokantaoperaatioihin
 */
@Singleton
class WalkRepository @Inject constructor(
    private val walkSessionDao: WalkSessionDao
) {

    /** Flow-virta kaikista kävelykerroista aikajärjestyksessä (uusin ensin) */
    val allSessions: Flow<List<WalkSession>> = walkSessionDao.getAllSessions()

    /**
     * Tallentaa uuden kävelykerran tietokantaan.
     * Kutsutaan kun kävelylenkki lopetetaan.
     * @param session Tallennettava kävelykerta
     */
    suspend fun insertSession(session: WalkSession) {
        walkSessionDao.insert(session)
    }

    /**
     * Päivittää olemassa olevan kävelykerran tiedot.
     * Käytetään esim. askelten tai matkan päivittämiseen kävelyn aikana.
     * @param session Päivitettävä kävelykerta
     */
    suspend fun updateSession(session: WalkSession) {
        walkSessionDao.update(session)
    }

    /**
     * Hakee parhaillaan aktiivisen kävelykerran.
     * @return Aktiivinen kävelykerta tai null
     */
    suspend fun getActiveSession(): WalkSession? {
        return walkSessionDao.getActiveSession()
    }
    
    /**
     * Poistaa kävelykerran tietokannasta.
     * @param session Poistettava kävelykerta
     */
    suspend fun deleteSession(session: WalkSession) {
        walkSessionDao.delete(session)
    }
}
