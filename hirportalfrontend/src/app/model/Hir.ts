import { Kategoria } from './Kategoria';
export interface Hir {
  id: number|null,
  cim: string,
  lejarat: string,
  szoveg: string,
  letrehozas: string,
  kategoriak: Kategoria[],
  keplink: string
}
