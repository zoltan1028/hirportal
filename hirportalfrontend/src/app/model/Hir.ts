import { HirFoOldal } from './HirFoOldal';
import { Kategoria } from './Kategoria';
import { Szerkeszto } from './Szerkeszto';
export interface Hir {
  id: number|null,
  cim: string,
  lejarat: string,
  szoveg: string,
  letrehozas: string,
  kategoriak: Kategoria[],
  keplink: string,
  isVezercikk: boolean,
  hirFooldal: HirFoOldal,
  szerkesztok: Szerkeszto[]
}
