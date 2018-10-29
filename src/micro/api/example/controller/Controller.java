package micro.api.example.controller;

import micro.api.example.interactor.Interactor;

public abstract class Controller {
	abstract public Interactor createInteractor();
}
