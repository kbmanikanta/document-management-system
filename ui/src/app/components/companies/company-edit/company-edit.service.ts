import { Subject } from 'rxjs';

export class CompanyEditService {

  backObserver = new Subject();
  undoObserver = new Subject();
  saveObserver = new Subject();
  editModeObserver = new Subject();

}
