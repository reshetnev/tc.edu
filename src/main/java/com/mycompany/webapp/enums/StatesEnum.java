package com.mycompany.webapp.enums;

public enum StatesEnum {
	
	DRAFT, PROPOSAL, REJECTED, NEW, OPEN, READY, IN_PROGRESS, FINISHED;
	
	public int getStateId()
    {
		int stateId;
        switch (this) {
        	case DRAFT: stateId=1; break;
        	case PROPOSAL: stateId=2; break;
        	case REJECTED: stateId=3; break;
        	case NEW: stateId=4; break;
        	case OPEN: stateId=5; break;
        	case READY: stateId=6; break;
        	case IN_PROGRESS: stateId=7; break;
        	case FINISHED: stateId=8; break;
        	default: stateId=0;
        }
        return stateId;
    }

}
