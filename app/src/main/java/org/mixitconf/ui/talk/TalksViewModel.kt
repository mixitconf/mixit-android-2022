package org.mixitconf.ui.talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import org.mixitconf.Properties
import org.mixitconf.model.dao.SpeakerRepository
import org.mixitconf.model.dao.TalkRepository
import org.mixitconf.model.entity.Speaker
import org.mixitconf.model.entity.Talk
import org.mixitconf.model.enums.TalkFormat
import org.mixitconf.toString

class TalksViewModel(
    private val repository: TalkRepository,
    private val speakerRepository: SpeakerRepository
) : ViewModel() {

    fun search(): LiveData<List<Talk>> = liveData {
        val result = repository.readAll()
        emit(result.sortedWith(compareBy<Talk> { it.start }.thenBy { it.end }.thenBy { it.room }))
    }

    fun searchFavorites(): LiveData<List<Talk>> = liveData {
        val result = repository.readAll()
        val favorites = result.filter { it.favorite }
        val favoriteDays = favorites.map { it.start.toString(Properties.TALK_LOCALDATE_FORMAT) }.distinct()
        val days = result
            .filter { it.format == TalkFormat.PLANNING_DAY }
            .filter { favoriteDays.contains(it.start.toString(Properties.TALK_LOCALDATE_FORMAT)) }
        emit(favorites.plus(days).sortedWith(compareBy<Talk> { it.start }.thenBy { it.end }.thenBy { it.room }))
    }

    fun getOne(id: String): LiveData<Talk?> = liveData {
        emit(repository.readOne(id))
    }

    fun getTalkSpeakers(speakerIds: List<String>): LiveData<List<Speaker>?> = liveData {
        emit(speakerRepository.readAllByIds(speakerIds).sortedBy { it.fullname })
    }

    fun update(talk: Talk): LiveData<Talk?> = liveData {
        repository.update(talk)
        emit(repository.readOne(talk.id))
    }
}