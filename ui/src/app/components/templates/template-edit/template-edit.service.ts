import { Subject } from 'rxjs';

export class TemplateEditService {

  backObserver = new Subject();
  undoObserver = new Subject();
  saveAsDraftObserver = new Subject();
  saveAsCompletedObserver = new Subject();
  editModeObserver = new Subject();

}
