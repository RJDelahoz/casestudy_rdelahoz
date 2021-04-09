package com.app.service;

import com.app.model.Memo;
import com.app.repo.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoService {

	private final MemoRepository memoRepository;

	@Autowired
	public MemoService(MemoRepository memoRepository) {
		this.memoRepository = memoRepository;
	}

	public void addMemo(Memo memo) {
		memoRepository.save(memo);
	}

}
