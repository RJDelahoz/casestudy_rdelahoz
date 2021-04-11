package com.app.service;

import com.app.dao.MemoDao;
import com.app.model.Memo;
import com.app.repo.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemoService implements MemoDao {

	private final MemoRepository memoRepository;

	@Autowired
	public MemoService(MemoRepository memoRepository) {
		this.memoRepository = memoRepository;
	}

	@Override
	public void addMemo(Memo memo) {
		memoRepository.save(memo);
	}
}
