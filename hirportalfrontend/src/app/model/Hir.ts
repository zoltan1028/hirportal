import { Kategoria } from './Kategoria';
export interface Hir {
  id: number,
  cim: string,
  lejarat: string,
  szoveg: string,
  kategoriak: Kategoria[]
}
