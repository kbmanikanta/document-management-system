import { Subject } from 'rxjs';

export class DocumentEditService {

  backObserver = new Subject();
  undoObserver = new Subject();
  saveObserver = new Subject();
  editModeObserver = new Subject();

}
