package com.eatin.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Je_tipa;

public interface JeTipaRepository extends JpaRepository<Je_tipa, Integer> {

	Long deleteByRestoran_idRestoranaAndTipRestorana_idTipaRestorana(int idRestoran, int idTip);

	Collection<Je_tipa> findByRestoran_idRestoranaAndTipRestorana_idTipaRestorana(int idRestoran, int idTip);

	Collection<Je_tipa> findByRestoran_idRestorana(int idRestoran);

}
