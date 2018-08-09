/*
  Copyright (c) 2018-present, Archx Contributors.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package co.en.archx.archx.medium;

/**
 * <p>
 *     Results, or better called as action results.
 *     <br><br>
 *     Example:<br>
 *     A result derived from performing the action.
 * </p>
 *
 * <pre> {@code
 *
 * LoadDogs -> DogsLoading
 * or
 * LoadDogs -> DogsLoadFail
 * or
 * LoadDogs -> DogsLoaded
 * } </pre>
 */
public interface Result { }
